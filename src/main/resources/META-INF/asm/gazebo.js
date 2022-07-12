// noinspection ES6ConvertVarToLetConst

var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');

// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {
    return {
        'gazeboSpawner': {
            'target': {
                'type': 'METHOD',
                'class': 'twilightforest/world/components/structures/finalcastle/FinalCastleBossGazeboComponent',
                'methodName': ASM.mapMethod('m_183269_'), // postProcess
                'methodDesc': '(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureFeatureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/core/BlockPos;)V'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.GETSTATIC),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new VarInsnNode(Opcodes.ALOAD, 5),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'io/github/jodlodi/twilighttweaks/ASMHooks',
                            'gazebo',
                            '(Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;)V',
                            false
                            ),
                        new InsnNode(Opcodes.RETURN)
                        )
                    );
                return methodNode;
            }
        }
    }
}
